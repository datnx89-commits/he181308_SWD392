# he181308_SWD392

## LAB211 — J1.S.P0056: Program to manage worker information

**Student:** HE181308 — Nguyễn Xuân Đạt
**Pattern:** MVC | **Language:** Java

## Features

1. Add a worker (validate: code not null/duplicated, age 18–50, salary > 0)
2. Increase salary (code must exist, amount > 0, history saved)
3. Decrease salary (code must exist, amount > 0, history saved)
4. Show adjusted salary worker information (sorted by worker code)
5. Quit

## Architecture (MVC + SOLID)

```
src/
├── model/
│   ├── Worker.java            # Entity: worker data
│   ├── SalaryStatus.java      # Enum: INCREASE / DECREASE
│   ├── SalaryHistory.java     # Entity: one salary adjustment record
│   ├── IWorkerManagement.java # Abstraction of business logic (DIP/OCP)
│   └── Management.java        # addWorker / changeSalary / getInfomationSalary
├── view/
│   └── WorkerView.java        # All console input/output (SRP)
├── controller/
│   └── WorkerController.java  # Coordinates View ↔ Model
└── Main.java                  # Entry point (wiring)
```

- **S**RP: entity / business logic / view / controller are separate classes.
- **O**CP: new storage or UI can be added by new implementations, without editing the controller.
- **L**SP: `Management` is substitutable anywhere `IWorkerManagement` is expected.
- **I**SP: interface exposes only the 3 required operations.
- **D**IP: `WorkerController` depends on `IWorkerManagement`, not on the concrete `Management`.

## Diagrams

See `diagrams/` — class diagram and sequence diagrams (add worker, change salary, show salary history).

## Build & run

```bash
javac -d out src/model/*.java src/view/*.java src/controller/*.java src/Main.java
java -cp out Main
```
