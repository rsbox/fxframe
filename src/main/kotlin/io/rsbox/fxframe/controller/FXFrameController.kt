package io.rsbox.fxframe.controller

import io.rsbox.fxframe.view.FXFrameViewport
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Node
import javafx.scene.image.Image
import tornadofx.Controller
import tornadofx.UIComponent
import tornadofx.onChangeOnce
import tornadofx.singleAssign

/**
 * The FXFrame borderless frame window controller.
 */
internal class FXFrameController : Controller() {

    /**
     * The initial view object to load.
     */
    internal var initView: UIComponent by singleAssign()

    /**
     * The stage / window icon.
     */
    internal var icon: Image? = null

    /**
     * Interaction Control Logic Start.
     */

    private var prevSizeX = 0
    private var prevSizeY = 0

    private var prevPosX = 0
    private var prevPosY = 0

    val maximized = SimpleBooleanProperty(false)
    val resizable = SimpleBooleanProperty(false)
    val snappable = SimpleBooleanProperty(false)

    private var snapped = false

    val moveHandle = SimpleObjectProperty<Node>()
    val viewport: FXFrameViewport by inject()

    init {
        moveHandle.onChangeOnce {
            if(it == null) return@onChangeOnce

            /**
             * Initialize all event listeners.
             */

        }
    }
}