import SwiftUI
import shared

struct ContentView: View {
    let calculator = Calculator.Companion()
    let greet = Greeting().greeting()
    
    @State private var firstNum: String = "0"
    @State private var secondNum: String = "0"
    private var sum: String {
        if let firstNum = Int32(firstNum), let secondNum = Int32(secondNum) {
            return String(calculator.sum(a: firstNum, b: secondNum))
        } else {
            return "🤔"
        }
    }
    
    var body: some View {
        VStack(alignment: .center) {
            Text(greet)
            HStack(alignment: .center) {
                TextField("A", text: $firstNum)
                    .keyboardType(.numberPad)
                    .multilineTextAlignment(.center)
                    .frame(width: 30)
                Text("+")
                TextField("B", text: $secondNum)
                    .keyboardType(.numberPad)
                    .multilineTextAlignment(.center)
                    .frame(width: 30)
                Text("=")
                Text(sum)
            }
        }
        .onAppear {
            kotlinTests()
        }
    }
    
    private func kotlinTests() {
        // Switching over enums
        /// Notes:  enums just work
        switch calculator.operation() {
        case .addition:
            print("addition")
        case .division:
            print("division")
        case .multiplication:
            print("multiplication")
        case .subtraction:
            print("subtraction")
        }
        
        
        // Switching over sealed classes
        /// Notes:  all properties hold a potential value, not clear which one _actually_ has one. Requires mapping to ´onEnum´
        switch onEnum(of: calculator.getStatus()) {
        case .loading(let string):
            print("loading with val:\(string)")
            print(string)
        case .error(let string):
            print("error with val: \(string)")
        }
        
        
        // Coroutines
        /// Notes: Can we do something good with errors here?
        Task {
            do {
                print("Suspendtask wait started")
                _ = try await calculator.asyncWork()
                print("Suspendtask wait finished")
            } catch {
                if let customException = error as? MyCustomExceptions {
                    switch onEnum(of: customException) {
                    case .invalidCode:
                        print("Error caught: InvalidCode")
                    case .invalidSessionCookie:
                        print("Error caught: InvalidSessionCookie")
                    case .userBlacklisted:
                        print("Error caught: UserBlacklisted")
                    }
                } else {
                    print("Something went wrong")
                }
            }
            
        }
        
        
        // Default parameters
        /// Notes: Creates a massive overhead
        calculator.someDefaultedOperation()
        calculator.someDefaultedOperation(param: 1)
        
        /// Notes: Me just creating two function definitions instead
        calculator.manuallyDefaultedOperation()
        
        
        // Functionconflicts
        /// Notes: string interface does not seem visable, but still works
        print(calculator.conflicting(a: 32))
        print(calculator.conflicting(a: "32"))
        
        
        // Flows
        /// Notes: Simply impressed by how this "just works"...
        Task {
            let flow = calculator.flowingIntegers()
            
            for await value in flow {
                print("Received flow value: \(value)")
            }
            
            print("Flow finished")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
