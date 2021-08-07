// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.rafgiav.separator_gen.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Provides controller functionality for application settings.
 */
public class ApplicationSettingsConfigurable implements Configurable {

    private ApplicationSettingsComponent mySettingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Separator-Gen";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new ApplicationSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        ApplicationSettingsState settings = ApplicationSettingsState.getInstance();
        boolean modified = !mySettingsComponent.getInnerLineStyle().equals(settings.innerLineStyle);
        modified |= mySettingsComponent.getLineLength() != settings.lineLength;
        return modified;
    }

    @Override
    public void apply() {
        ApplicationSettingsState settings = ApplicationSettingsState.getInstance();
        settings.innerLineStyle = mySettingsComponent.getInnerLineStyle();
        settings.lineLength = mySettingsComponent.getLineLength();
    }

    @Override
    public void reset() {
        ApplicationSettingsState settings = ApplicationSettingsState.getInstance();
        mySettingsComponent.setInnerLineStyle(settings.innerLineStyle);
        mySettingsComponent.setLineLength(settings.lineLength);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
