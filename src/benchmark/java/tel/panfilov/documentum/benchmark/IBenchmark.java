package tel.panfilov.documentum.benchmark;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public interface IBenchmark {

    void doSetup();

    void doOp();

    void doRelease();

}
