package io.rsbox.fxframe

import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.stage.StageStyle
import io.rsbox.fxframe.controller.FXFrameController
import io.rsbox.fxframe.view.FXFrameTitleBar
import io.rsbox.fxframe.view.FXFrameViewport
import javafx.scene.Node
import tornadofx.*

/**
 * The Primary FXFrame JavaFX Application abstraction.
 */
abstract class FXFrameApp : App(FXFrameViewport::class, FXFrameStyle::class) {

    private val controller: FXFrameController by inject()

    /**
     * The skin theme for the FX borderless frame.
     */
    abstract val skin: FXFrameSkin

    /**
     * The primary view to load.
     */
    abstract val view: UIComponent

    /**
     * Sets the Stage window icon.
     */
    fun setFXFrameIcon(icon: Image) {
        setStageIcon(icon)
        controller.icon = icon
    }

    /**
     * Enables window dragging.
     */
    fun enableMoving(node: Node = find<FXFrameTitleBar>().root) {
        controller.moveHandle.value = node
    }

    /**
     * Enables windows snapping.
     */
    fun enableSnapping() {
        controller.snappable.value = true
    }

    /**
     * Enables window resizing.
     */
    fun enableResizing() {
        controller.resizable.value = true
    }

    override fun init() {
        importStylesheet(skin.stylesheet)

        /**
         * Set the view
         */
        controller.initView = view

        super.init()
    }

    override fun start(stage: Stage) {
        stage.initStyle(StageStyle.UNDECORATED)
        super.start(stage)
    }
}