package gui.views

import gui.controllers.ControllerHelp
import tornadofx.View
import tornadofx.borderpane
import tornadofx.label

class ViewHelp: View() {
    private val sideController: ControllerHelp by inject()

    override val root = borderpane {
        center = label("Окно помощи")
    }
}