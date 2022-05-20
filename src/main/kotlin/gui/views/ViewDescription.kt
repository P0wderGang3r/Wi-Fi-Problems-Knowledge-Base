package gui.views

import gui.controllers.ControllerDescription
import tornadofx.View
import tornadofx.borderpane
import tornadofx.label

class ViewDescription: View() {
    private val sideController: ControllerDescription by inject()

    override val root = borderpane {
        center = label("Окно описания БЗ")
    }
}