package com.pingidentity.dg.spel_helpers;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link SensitiveWords}.
 *
 */
public class SensitiveWordsTest {

  @Test
  public void shouldRedactContent() {
    Assert.assertEquals("The k***** is you!",
        SensitiveWords.redactContent("The killer is you!"));
  }

  @Test
  public void shouldUseExistingCase() {
    Assert.assertEquals("The K***** is you!",
        SensitiveWords.redactContent("The KiLlEr is you!"));
  }

  @Test
  public void shouldIgnoreDefaultsWhenUsingExternalFile() {
    Assert.assertEquals("Programmers are the worst.",
        SensitiveWords.redactContent("Programmers are the worst."));
  }

}

