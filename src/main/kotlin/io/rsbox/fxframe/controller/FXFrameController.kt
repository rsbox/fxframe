package io.rsbox.fxframe.controller

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
    internal var initView: UIComponent by singleAssign()

    /**
     * The stage / window icon.
     */
    internal var icon: Image? = null


}