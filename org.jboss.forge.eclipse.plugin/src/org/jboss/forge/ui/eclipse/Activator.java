package org.jboss.forge.ui.eclipse;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.jboss.forge.ui.eclipse.integration.ForgeService;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{

   // The plug-in ID
   public static final String PLUGIN_ID = "org.jboss.forge.ui.eclipse"; //$NON-NLS-1$

   // The shared instance
   private static Activator plugin;

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
   public void start(BundleContext context) throws Exception
   {
      super.start(context);
      ForgeService.INSTANCE.start();
      plugin = this;
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
