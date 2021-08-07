// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed
// by the Apache 2.0 license that can be found in the LICENSE file.

package com.rafgiav.separator_gen.actions;

import com.intellij.openapi.editor.Document;

public class SubstitutionWriterRunnable implements Runnable {

  private final Document document;
  private final int startPos;
  private final int endPos;
  private final String substitution;

  public SubstitutionWriterRunnable(Document document, String substitution, int startPos, int endPos) {
    this.document = document;
    this.substitution = substitution;
    this.startPos = startPos;
    this.endPos = endPos;
  }

  @Override
  public void run() {
    document.replaceString(
            startPos,
            endPos,
            substitution);
  }
}
