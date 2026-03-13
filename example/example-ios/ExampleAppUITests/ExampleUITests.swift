import XCTest
import ExampleShared

class ExampleUITests: XCTestCase {

    var adapter: Kaluga_uitestDefaultApplicationAdapter!

    override func setUp() {
        super.setUp()
        continueAfterFailure = false
        adapter = IosAdapterKt.createAdapter()
        adapter.launch(identifier: nil, arguments: [:])
    }

    override func tearDown() {
        adapter.tearDown()
        super.tearDown()
    }

    func testTapButton() {
        ExampleScenariosKt.runTapButtonScenario(adapter: adapter)
    }

    func testTypeText() {
        ExampleScenariosKt.runTypeTextScenario(adapter: adapter)
    }

    func testToggleSwitch() {
        ExampleScenariosKt.runToggleSwitchScenario(adapter: adapter)
    }

    func testScrollList() {
        ExampleScenariosKt.runScrollListScenario(adapter: adapter)
    }
}
