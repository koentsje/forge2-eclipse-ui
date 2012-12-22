/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.ui.eclipse.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * A Forge Wizard Page
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 *
 */
public class ForgeWizardPage extends WizardPage
{

   public ForgeWizardPage(String pageName)
   {
      super(pageName);
   }

   protected ForgeWizardPage(String pageName, String title, ImageDescriptor titleImage)
   {
      super(pageName, title, titleImage);
   }

   @Override
   public void createControl(Composite parent)
   {

   }

}
