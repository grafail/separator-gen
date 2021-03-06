package com.rafgiav.separator_gen;

import com.intellij.lang.Commenter;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageCommenters;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeparatorGenerator {

  private SeparatorGenerator() {}

  public static String generateHorizontalSeparator(
      String content, String innerLineStyle, Language lang, int lineLength) {
    String commentChar = SeparatorGenerator.getCommentChars(lang);
    if (commentChar != null) {
      int availableLineSpace = lineLength - content.length() - 4 - (2 * commentChar.length());
      return String.format(
          "%s %s %s %s %s",
          commentChar,
          innerLineStyle.repeat(availableLineSpace / 2 + availableLineSpace % 2),
          content,
          innerLineStyle.repeat(availableLineSpace / 2),
          commentChar);
    }
    return null;
  }

  public static boolean isGenerationPossible(String content, int lineLength, String commentChars) {
    return !content.contains("\n") && content.length() + 4 + 2*commentChars.length() <= lineLength;
  }

  public static String repairSeparator(
      String content, Language lang, int lineLength, String innerLineStyle) {
    String commentChars = getCommentChars(lang);
    if (commentChars != null) {
      Pattern sepRegex =
          Pattern.compile(
              "^"
                  + Pattern.quote(commentChars)
                  + "[ ]*[-_─]+[ ]*(?<content>[a-z]+)[ ]*[-_─]+[ ]*"
                  + Pattern.quote(commentChars)
                  + "$");
      if (!content.contains("\n")) {
        Matcher matcher = sepRegex.matcher(content);
        if (matcher.find()) {
          String detectedContent = matcher.group("content");
          if (SeparatorGenerator.isGenerationPossible(detectedContent, lineLength, commentChars)) {
            return generateHorizontalSeparator(detectedContent, innerLineStyle, lang, lineLength);
          }
        }
      }
    }
    return null;
  }

  private static String getCommentChars(Language lang) {
    if(lang == null){
      return "#";
    }
    @NotNull List<Commenter> commenters = LanguageCommenters.INSTANCE.allForLanguage(lang);
    if (!commenters.isEmpty()) {
      String commentSep = commenters.get(0).getLineCommentPrefix();
      return commentSep!=null?commentSep.strip():null;
    }
    return null;
  }
}
