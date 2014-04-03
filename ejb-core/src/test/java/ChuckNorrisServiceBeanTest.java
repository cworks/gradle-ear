/**
 * Created with love by corbett.
 * User: corbett
 * Date: 4/2/14
 * Time: 9:57 PM
 */
import org.cworks.ejb.ChuckNorrisServiceBean;
import org.cworks.ejb.client.ChuckNorrisService;
import org.junit.Test;

public class ChuckNorrisServiceBeanTest {

    @Test
    public void testRandomJokeRequest() {
        ChuckNorrisService service = new ChuckNorrisServiceBean();
        String joke = service.randomJoke();
        System.out.println("joke> " + joke);
    }

}
