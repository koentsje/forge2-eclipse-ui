/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.ui.eclipse.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * Forge Wizard
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 *
 */
public class ForgeWizard extends Wizard implements INewWizard
{

   public ForgeWizard()
   {
      // TODO Auto-generated constructor stub
   }

   @Override
   public void init(IWorkbench workbench, IStructuredSelection selection)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean performFinish()
   {
      // TODO Auto-generated method stub
      return false;
   }

}