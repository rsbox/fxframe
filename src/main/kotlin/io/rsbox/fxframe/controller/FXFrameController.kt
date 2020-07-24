package io.rsbox.fxframe.controller

import io.rsbox.fxframe.view.FXFrameViewport
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Rectangle2D
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.stage.Screen
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

    private var prevSizeX = 0.0
    private var prevSizeY = 0.0

    private var prevPosX = 0.0
    private var prevPosY = 0.0

    val maximized = SimpleBooleanProperty(false)
    val resizable = SimpleBooleanProperty(false)
    val snappable = SimpleBooleanProperty(false)

    private var snapped = false

    val moveHandle = SimpleObjectProperty<Node>()
    val viewport: FXFrameViewport by inject()

    private val stage get() = primaryStage

    /**
     * Initializes the controller window controls.
     */
    internal fun init() {
        prevSizeX = stage.width
        prevSizeY = stage.height
        prevPosX = stage.x
        prevPosY = stage.y
    }

    /**
     * Maximizes the window.
     */
    fun maximize() {
        /**
         * The visible bounding rectangle of the screen.
         */
        val screen: Rectangle2D = if(Screen.getScreensForRectangle(stage.x, stage.y, stage.width / 2, stage.height / 2).isEmpty()) {
            Screen.getScreensForRectangle(stage.x, stage.y, stage.width, stage.height)[0].visualBounds
        } else {
            Screen.getScreensForRectangle(stage.x, stage.y, stage.width / 2, stage.height / 2)[0].visualBounds
        }

        if(maximized.get()) {
            stage.width = prevSizeX
            stage.height = prevPosY
            stage.x = prevPosX
            stage.y = prevPosY
            setMaximized(false)
        } else {
            if(!snapped) {
                prevSizeX = stage.width
                prevSizeY = stage.height
                prevPosX = stage.x
                prevPosY = stage.y
            }
            else if(!screen.contains(prevPosX, prevPosY)) {
                if(prevSizeX > screen.width) {
                    prevSizeX = screen.width - 20
                }

                if(prevSizeY > screen.height) {
                    prevSizeY = screen.height - 20
                }

                prevPosX = screen.minX + (screen.width - prevSizeX) / 2
                prevPosY = screen.minY + (screen.height - prevSizeY) / 2
            }

            stage.x = screen.minX
            stage.y = screen.minY
            stage.width = screen.width
            stage.height = screen.height

            setMaximized(true)
        }
    }

    fun minimize() {
        stage.isIconified = true
    }

    private fun setMaximized(value: Boolean) {
        this.maximized.set(value)
        setResizable(!value)
    }

    private fun setResizable(value: Boolean) {
        resizable.set(value)
    }
}