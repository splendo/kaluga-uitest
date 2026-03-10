import SwiftUI

struct ContentView: View {
    @State private var resultVisible = false
    @State private var textValue = ""
    @State private var switchChecked = false

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                Button("Tap me") {
                    resultVisible = true
                }
                .accessibilityIdentifier("example_button")

                if resultVisible {
                    Text("Button tapped!")
                        .accessibilityIdentifier("example_result_text")
                }

                TextField("Enter text", text: $textValue)
                    .accessibilityIdentifier("example_text_field")
                    .textFieldStyle(.roundedBorder)

                Toggle("Switch", isOn: $switchChecked)
                    .accessibilityIdentifier("example_switch")

                LazyVStack(alignment: .leading) {
                    ForEach(0..<10, id: \.self) { index in
                        Text("Item \(index)")
                            .accessibilityIdentifier("example_item_\(index)")
                            .padding(.vertical, 8)
                    }
                }
                .accessibilityIdentifier("example_list")
            }
            .padding()
        }
    }
}
