package com.koto.keddit

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * For now it runs only in gradle, not in Android Studio.
 */
@RunWith(JUnitPlatform::class)
class NewsManagerSpekTest : Spek({
    it("should pass") {
        assert(true)
    }
})