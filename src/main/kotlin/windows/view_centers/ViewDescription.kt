package windows.view_centers

import tornadofx.View
import tornadofx.borderpane
import tornadofx.label
import tornadofx.useMaxHeight
import windows.controllers.ControllerDescription

class ViewDescription: View() {
    private val sideController: ControllerDescription by inject()

    override val root = borderpane {
        center = label("Окно описания БЗ")
    }
}