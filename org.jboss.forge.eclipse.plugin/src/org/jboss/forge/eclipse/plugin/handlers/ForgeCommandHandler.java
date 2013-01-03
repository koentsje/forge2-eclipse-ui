package org.jboss.forge.eclipse.plugin.handlers;

import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.jboss.forge.container.Addon;
import org.jboss.forge.ui.eclipse.integration.ForgeService;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ForgeCommandHandler extends AbstractHandler
{
   /**
    * The constructor.
    */
   public ForgeCommandHandler()
   {
   }

   /**
    * the command has been executed, so extract extract the needed information from the application context.
    */
   public Object execute(ExecutionEvent event) throws ExecutionException
   {
      IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
      String message = "";

      Set<Addon> addons = ForgeService.INSTANCE.getAddonRegistry().getRegisteredAddons();

      int size = addons.size();
      
      for (Addon addon : addons)
      {
         message += addon + "\n";
      }

      MessageDialog.openInformation(window.getShell(), "Uiview", message);
      return null;
   }
}
