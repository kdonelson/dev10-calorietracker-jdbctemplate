
# Calorie Tracker

## Models

**`LogEntry` Class**

* `id` - `int`
* `loggedOn` - `String`
* `type` - `LogEntryType`
* `description` - `String`
* `calories` - `int`

**LogEntryType Enum**

* BREAKFAST
* LUNCH
* DINNER
* SNACK

## Tasks

### Project Set Up

* [x] Set up the Maven project
* [x] Add JUnit dependency

### Models

* [x] Define the `LogEntry` model
* [x] Define a `LogEntryType` enumeration

### Data Layer

* [x] Stub out a test data CSV file

* [ ] Define the `LogEntryFileRepository` class
  * [x] `List<LogEntry> findAll()`
  * [x] `List<LogEntry> findByType(LogEntryType type)`
  * [x] `LogEntry findById(int id)`
  * [x] `LogEntry create(LogEntry entry)`
  * [ ] `boolean update(LogEntry entry)`
  * [ ] `boolean deleteById(int id)`

* [ ] Write tests interactively with implementing repository methods
  * To save time, stub out some tests with TODOs

* [x] Create custom `DataAccessException` class

* [ ] Update `LogEntryFileRepositoryTest` class with known good state !!!
  * Add seed and test data files
  * Use file I/O to copy the seed file over the test file before each test

* [x] Add helper method to assert model field values

#### JdbcTemplate Repository

* [x] Create database schema
* [x] Create database DDL and DML script
* [x] Update the `LogEntryType` enum with `value` field
* [x] Add the appropriate Spring dependencies to `pom.xml`
* [x] Update the project to use Spring annotation configuration

* [ ] Create a test-specific Spring configuration class
* [ ] Create a new concrete implementation of the project's repository interface that uses `JdbcTemplate`
* [ ] Start with empty method bodies that return a default value
* [ ] Generate tests for the `JdbcTemplate` repository and complete methods one at a time with accompanying tests
* [ ] Create a test database to safely execute tests and establish known good state
* [ ] Implement the repository methods while writing tests

### Domain Layer

* [ ] Define the `LogEntryService` class
  * [x] `List<LogEntry> findAll()`
  * [x] `List<LogEntry> findByType(LogEntryType type)`
  * [x] `LogEntry findById(int id)`
  * [ ] `LogEntryResult create(LogEntry entry)`
  * [ ] `LogEntryResult update(LogEntry entry)`
  * [ ] `LogEntryResult deleteById(int id)`

* [ ] Define the `LogEntryResult` result class
  * [ ] `List<String> getErrorMessages()`
  * [ ] `void addErrorMessage(String message)`
  * [ ] `void addErrorMessage(String format, Object... args)`
  * [ ] `boolean isSuccess()`
  * [ ] `LogEntry getLogEntry()`
  * [ ] `void setLogEntry()`

* [x] Extract `LogEntryRepository` interface
  * Pass the repository as a dependency to the `LogEntryService` class

* [x] Define `LogEntryServiceTest` class
  * Instantiate a repository instance before each test to create a known good state

* [x] Define `LogEntryRepositoryDouble` test double class

* [ ] Define model validations (input and domain)
  * Required values
  * Positive number for calories
  * Max calories???
  * Max description length???
  * Restrict how many entries per type for a single day???

* [ ] Write tests interactively with implementing repository methods
  * To save time, stub out some tests with TODOs

### UI Layer

* [ ] Define the `Controller` class
  * Dependent on the `LogEntryService` and `View`

* [ ] Define the `View` class

* [x] Update the `App` class
  * Instantiate dependencies
  * Instantiate an instance of the `Controller` class

* [ ] Define `Controller` class methods
  * [ ] `void run()`
  * [ ] `void displayLogEntries()`
  * [ ] `void createLogEntry()`
  * [ ] `void updateLogEntry()`
  * [ ] `void deleteLogEntry()`

* [ ] Define `View` class methods
  * [ ] `int displayMenuAndSelect()`
  * [ ] `void printHeader(String header)`
  * [ ] `String readString(String prompt)`
  * [ ] `String readRequiredString(String prompt)`
  * [ ] `int readInt(String prompt)`
  * [ ] `int readInt(String prompt, int min, int max)`
  * [ ] `void displayLogEntries(List<LogEntry> entries)`
  * [ ] `LogEntry makeLogEntry()`
  * [ ] `void displayResult(LogEntryResult result)`
  * [ ] `LogEntry findLogEntry(List<LogEntry> entries)`
  * [ ] `LogEntry updateLogEntry(List<LogEntry> entries)`
  * [ ] `int getLogEntryIdToDelete(List<LogEntry> entries)`

* [ ] Define global exception handler in the `run` method
  * Split the `run()` method into `run()` and `runApp()` methods
  * Add a `try/catch` to the `run()` method
