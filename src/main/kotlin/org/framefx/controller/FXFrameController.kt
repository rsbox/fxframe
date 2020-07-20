package org.framefx.controller

import javafx.scene.image.Image
import tornadofx.Controller
import tornadofx.UIComponent
import tornadofx.singleAssign

/**
 * The FXFrame borderless frame window controller.
 */
class FXFrameController : Controller() {

    /**
     * The initial view object to load.
     */
    var initView: UIComponent by singleAssign()

    /**
     * The stage / window icon.
     */
    var icon: Image? = null

    /**
     * Window control statuses
     */
    var maximized: Boolean = false
    var minimized: Boolean = false
}