// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed
// by the Apache 2.0 license that can be found in the LICENSE file.

package com.rafgiav.separator_gen.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.rafgiav.separator_gen.SeparatorGenerator;
import com.rafgiav.separator_gen.settings.ApplicationSettingsState;
import org.jetbrains.annotations.NotNull;

public class SeparatorGeneratorAction extends AnAction {

  public SeparatorGeneratorAction() {
    super();
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    int lineLength = ApplicationSettingsState.getInstance().lineLength;
    String innerLineStyle = ApplicationSettingsState.getInstance().innerLineStyle;
    Editor editor = event.getData(CommonDataKeys.EDITOR);
    PsiFile pf = event.getData(CommonDataKeys.PSI_FILE);
    if (editor != null && pf != null) {
      @NotNull SelectionModel selectionModel = editor.getSelectionModel();
      String text;
      if (selectionModel.hasSelection()
          && (text = selectionModel.getSelectedText()) != null
          && SeparatorGenerator.isGenerationPossible(text, lineLength)) {
        String generatedSeparator =
            SeparatorGenerator.generateHorizontalSeparator(text, innerLineStyle, pf.getLanguage(), lineLength);
        if (generatedSeparator != null) {
          SubstitutionWriterRunnable writerRunnable =
              new SubstitutionWriterRunnable(
                  editor.getDocument(),
                  generatedSeparator,
                  selectionModel.getSelectionStart(),
                  selectionModel.getSelectionEnd());
          WriteCommandAction.runWriteCommandAction(editor.getProject(), writerRunnable);
        }
      }
    }
  }

  /**
   * Determines whether this menu item is available for the current context. Requires a project to
   * be open.
   *
   * @param e Event received when the associated group-id menu is chosen.
   */
  @Override
  public void update(AnActionEvent e) {
    // Set the availability based on whether a project is open
    Project project = e.getProject();
    int lineLength = ApplicationSettingsState.getInstance().lineLength;
    boolean visible = project != null;
    boolean enabled = false;
    if (visible) {
      Editor editor = e.getData(CommonDataKeys.EDITOR);
      if (editor != null) {
        @NotNull SelectionModel selectionModel = editor.getSelectionModel();
        String text;
        if (selectionModel.hasSelection()
            && (text = selectionModel.getSelectedText()) != null
            && SeparatorGenerator.isGenerationPossible(text, lineLength)) {
          enabled = true;
        }
      }
    }
    e.getPresentation().setEnabled(enabled);
    e.getPresentation().setVisible(visible);
  }
}
