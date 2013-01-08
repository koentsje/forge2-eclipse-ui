package org.jboss.forge.ui.eclipse;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.jboss.forge.container.Forge;
import org.jboss.forge.container.util.ClassLoaders;
import org.jboss.forge.se.init.ClassLoaderAdapterCallback;
import org.jboss.forge.ui.eclipse.integration.ForgeService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;

import bootpath.BootpathMarker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{

   // The plug-in ID
   public static final String PLUGIN_ID = "org.jboss.forge.ui.eclipse"; //$NON-NLS-1$

   // The shared instance
   private static Activator plugin;

   private URLClassLoader loader;

   /**
    * The constructor
    */
   public Activator()
   {
   }

   /*
    * (non-Javadoc)
    *
    * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
    */
   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);
      Forge forge = getForge(context);
      ForgeService.INSTANCE.setForge(forge);
      ForgeService.INSTANCE.start(loader);
      plugin = this;
   }

   private Forge getForge(final BundleContext context)
   {
      return ClassLoaders.executeIn(loader, new Callable<Forge>()
      {
         @Override
         public Forge call() throws Exception
         {
            BundleWiring wiring = context.getBundle().adapt(BundleWiring.class);
            Collection<String> entries = wiring.listResources("bootpath", "*.jar", BundleWiring.LISTRESOURCES_LOCAL);
            Collection<URL> resources = new HashSet<URL>();
            File jarDir = File.createTempFile("forge", "jars");
            if (entries != null)
               for (String resource : entries)
               {
                  URL jar = BootpathMarker.class.getResource("/" + resource);
                  if (jar != null)
                  {
                     resources.add(copy(jarDir, resource, jar.openStream()));
                  }
               }

            loader = new URLClassLoader(resources.toArray(new URL[resources.size()]), null);

            Class<?> bootstrapType = loader.loadClass("org.jboss.forge.container.ForgeImpl");

            Forge forge = (Forge)
                     ClassLoaderAdapterCallback.enhance(Forge.class.getClassLoader(), loader,
                              bootstrapType.newInstance(),
                              Forge.class);
            return forge;
         }
      });
   }

   private URL copy(File directory, String name, InputStream input) throws IOException
   {
      File outputFile = new File(directory, name);

      FileOutputStream output = null;
      try
      {
         directory.delete();
         outputFile.getParentFile().mkdirs();
         outputFile.createNewFile();

         output = new FileOutputStream(outputFile);
         final byte[] buffer = new byte[4096];
         int read = 0;
         while ((read = input.read(buffer)) != -1)
         {
            output.write(buffer, 0, read);
         }
         output.flush();
      }
      catch (Exception e)
      {
         throw new RuntimeException("Could not write out jar file " + name, e);
      }
      finally
      {
         close(input);
         close(output);
      }
      return outputFile.toURI().toURL();
   }

   private void close(Closeable closeable)
   {
      try
      {
         if (closeable != null)
         {
            closeable.close();
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException("Could not close stream", e);
      }
   }

   /*
    * (non-Javadoc)
    *
    * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
    */
   @Override
   public void stop(BundleContext context) throws Exception
   {
      plugin = null;
      super.stop(context);
      ForgeService.INSTANCE.stop();
   }

   /**
    * Returns the shared instance
    *
    * @return the shared instance
    */
   public static Activator getDefault()
   {
      return plugin;
   }

   /**
    * Returns an image descriptor for the image file at the given plug-in relative path
    *
    * @param path the path
    * @return the image descriptor
    */
   public static ImageDescriptor getImageDescriptor(String path)
   {
      return imageDescriptorFromPlugin(PLUGIN_ID, path);
   }
}
