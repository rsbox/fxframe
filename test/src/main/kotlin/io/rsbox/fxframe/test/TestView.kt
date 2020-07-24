package io.rsbox.fxframe.test

import tornadofx.View
import tornadofx.pane

/**
 * The TestApp JavaFX view.
 */
class TestView : View("Test Application") {

    override val root = pane {
        setPrefSize(1000.0, 750.0)
    }
}