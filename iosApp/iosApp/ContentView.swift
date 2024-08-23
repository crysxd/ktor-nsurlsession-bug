import SwiftUI
import shared

struct ContentView: View {
	var body: some View {
        AppUiController()
	}
}

struct AppUiController : UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> some UIViewController {
        AppUiControllerKt.AppUiController()
    }

    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {

    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ContentView()
	}
}
