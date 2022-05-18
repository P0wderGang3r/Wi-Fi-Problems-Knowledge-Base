package windows.view_centers

import tornadofx.View
import tornadofx.borderpane
import tornadofx.label
import windows.controllers.ControllerHelp

class ViewHelp: View() {
    private val sideController: ControllerHelp by inject()

    override val root = borderpane {
        center = label("Окно помощи")
    }
}