package tel.panfilov.documentum.utils.bulk;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public interface ISkipChecker {

    boolean skip(String objectId);

}
