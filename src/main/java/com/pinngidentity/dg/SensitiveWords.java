package com.pingidentity.dg.spel_helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sensitive words.
 *
 */
public class SensitiveWords {

  private static final Character REDACT_CHAR = '*';

  /* @formatter:off */
  private static final List<String> DEFAULT_DENY_LIST = Arrays.asList(
      "killer",
      "America",
      "blind",
      "wealthy",
      "wrong",
      "like",
      "quit",
      "laughing",
      "termite",
      "change",
      "monkeys",
      "understand",
      "lipstick",
      "breaks",
      "exception",
      "knocking",
      "programmers",
      "impossible",
      "dam"
  );
  /* @formatter:on */

  private static List<String> DENY_LIST = DEFAULT_DENY_LIST;

  static {
    InputStream is = SensitiveWords.class.getClassLoader()
        .getResourceAsStream("spel_helpers/sensitive-words.txt");
    if (is != null) {
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(is))) {
        DENY_LIST = reader.lines().map(String::trim)
            .collect(Collectors.toList());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private SensitiveWords() {
    throw new UnsupportedOperationException(
        String.format("%s is a utility class that should not be instantiated.",
            SensitiveWords.class.getSimpleName()));
  }

  /**
   * Redacts content by tokenizing and redacting anything in the deny list.
   *
   * @param content The input body of content.
   * @return The redacted content.
   */
  public static String redactContent(String content) {
    if (content != null) {
      return Arrays.stream(content.split(" "))
          .map(SensitiveWords::redactWordIfSensitive)
          .collect(Collectors.joining(" "));
    }
    return content;
  }

  private static String redactWordIfSensitive(String word) {
    /* @formatter:off */
    return DENY_LIST.stream()
        .filter(word::equalsIgnoreCase)
        .map(dontCare -> word)
        .findFirst()
        .map(SensitiveWords::redactWord)
        .orElse(word);
    /* @formatter:on */
  }

  private static String redactWord(String sensitive) {
    if (sensitive != null) {
      StringBuilder builder = new StringBuilder("" + sensitive.charAt(0));
      for (int i = 1; i < sensitive.length(); i++) {
        builder.append(REDACT_CHAR);
      }
      return builder.toString();
    }
    return sensitive;
  }
}
