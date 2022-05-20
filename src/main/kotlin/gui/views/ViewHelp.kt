package gui.views

import tornadofx.View
import tornadofx.borderpane
import tornadofx.label
import gui.controllers.ControllerHelp

class ViewHelp: View() {
    private val sideController: ControllerHelp by inject()

    override val root = borderpane {
        center = label("Окно помощи")
    }
}