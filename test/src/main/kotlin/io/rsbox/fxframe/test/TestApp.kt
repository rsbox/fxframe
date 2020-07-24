package io.rsbox.fxframe.test

import io.rsbox.fxframe.FXFrameApp
import io.rsbox.fxframe.FXFrameSkin
import javafx.scene.image.Image
import tornadofx.find
import tornadofx.launch

/**
 * A simple test FXFrame application used during development to test
 * the functionality of features.
 *
 * Also used to test for any bugs should they appear.
 */
class TestApp : FXFrameApp() {

    override val skin = FXFrameSkin.ARCDARK
    override val view = find<TestView>()

    init {
        setFXFrameIcon(Image("/icon.png"))

        /**
         * Enable controls
         */
        enableMoving()
        enableResizing()
        enableSnapping()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch<TestApp>()
        }
    }
}