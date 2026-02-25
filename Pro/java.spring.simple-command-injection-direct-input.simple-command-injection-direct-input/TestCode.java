@RestController
public class SQLIController {
    private final CustomerInformationRepository repository;

    @GetMapping("/run/{command}")
    public ResponseEntity<?> run1(
        @PathVariable final String command
    ) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();
        try {
            String foo = command + "something something...";
            // ok:simple-command-injection-direct-input
            Runtime.getRuntime().exec(foo); // ok because it was concatenated, not a direct input
        } catch (IOException e) {
           response = ResponseEntity.badRequest().build();
        }

        return response;
    }

    @GetMapping("/run/{command}")
    public ResponseEntity<?> run_direct_from_jumbo(
        @PathVariable final String command
    ) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();
        try {
            // ruleid:simple-command-injection-direct-input
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
           response = ResponseEntity.badRequest().build();
        }

        return response;
    }

    @GetMapping("/run/{command}")
    public ResponseEntity<?> ok(
        @PathVariable final String command
    ) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();
        try {
            // ok:simple-command-injection-direct-input
            Runtime.getRuntime().exec("/bin/ls");
        } catch (IOException e) {
           response = ResponseEntity.badRequest().build();
        }

        return response;
    }
}
