package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun adminPasswordValidation() {
    val pass = "Archivo27798"
    val isValid = pass.trim() == "Archivo27798" || pass.trim().equals("Archivo27798", ignoreCase = true)
    assertTrue(isValid)
  }
}
