package windows.view_centers

import tornadofx.*
import windows.controllers.ControllerDiagnostics

class ViewDiagnostics: View() {
    private val sideController: ControllerDiagnostics by inject()

    override val root = borderpane {
        center = label("Окно диагностики")
    }
}