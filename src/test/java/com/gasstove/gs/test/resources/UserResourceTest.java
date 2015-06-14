package com.gasstove.gs.test.resources;

import com.gasstove.gs.models.User;
import com.gasstove.gs.resources.UserResource;
import com.gasstove.gs.test.TestConfiguration;

public class UserResourceTest extends AbstractResourceTest  {

    public UserResourceTest(){
        this.clath = User.class;
        this.resource = new UserResource(TestConfiguration.db);
        this.getAll_exp = "[{\"first\":\"Kareem\",\"last\":\"Kai\",\"id\":1},{\"first\":\"Ceola\",\"last\":\"Cue\",\"id\":2},{\"first\":\"Amalia\",\"last\":\"Adames\",\"id\":3},{\"first\":\"Sung\",\"last\":\"Sebesta\",\"id\":4},{\"first\":\"Shantay\",\"last\":\"Schaaf\",\"id\":5},{\"first\":\"Tien\",\"last\":\"Tallmadge\",\"id\":6},{\"first\":\"Yon\",\"last\":\"Yerby\",\"id\":7},{\"first\":\"Candida\",\"last\":\"Cornell\",\"id\":8},{\"first\":\"Catrice\",\"last\":\"Chapple\",\"id\":9},{\"first\":\"Florentina\",\"last\":\"Foret\",\"id\":10},{\"first\":\"Sylvia\",\"last\":\"Shehane\",\"id\":11},{\"first\":\"Cornelius\",\"last\":\"Chau\",\"id\":12},{\"first\":\"Teresa\",\"last\":\"Torrain\",\"id\":13},{\"first\":\"Emery\",\"last\":\"Ebron\",\"id\":14},{\"first\":\"Jeraldine\",\"last\":\"Jolin\",\"id\":15},{\"first\":\"Ed\",\"last\":\"Eggen\",\"id\":16},{\"first\":\"Karyn\",\"last\":\"Kluth\",\"id\":17},{\"first\":\"Dorcas\",\"last\":\"Desch\",\"id\":18},{\"first\":\"Cleveland\",\"last\":\"Chevere\",\"id\":19},{\"first\":\"Beatrice\",\"last\":\"Bernhardt\",\"id\":20},{\"first\":\"Takako\",\"last\":\"Troxel\",\"id\":21},{\"first\":\"Elroy\",\"last\":\"Eldredge\",\"id\":22},{\"first\":\"Leila\",\"last\":\"Longstreet\",\"id\":23},{\"first\":\"Georgia\",\"last\":\"Grimsley\",\"id\":24},{\"first\":\"Alan\",\"last\":\"Artis\",\"id\":25},{\"first\":\"Deetta\",\"last\":\"Daring\",\"id\":26},{\"first\":\"Nicol\",\"last\":\"Nay\",\"id\":27},{\"first\":\"Emilie\",\"last\":\"Eccles\",\"id\":28},{\"first\":\"Stepanie\",\"last\":\"Salters\",\"id\":29},{\"first\":\"Maynard\",\"last\":\"Magnusson\",\"id\":30},{\"first\":\"Cherrie\",\"last\":\"Cotner\",\"id\":31},{\"first\":\"Lincoln\",\"last\":\"Lacourse\",\"id\":32},{\"first\":\"Launa\",\"last\":\"Loud\",\"id\":33},{\"first\":\"Caroline\",\"last\":\"Crooms\",\"id\":34},{\"first\":\"Mason\",\"last\":\"Michalak\",\"id\":35},{\"first\":\"Janice\",\"last\":\"James\",\"id\":36},{\"first\":\"Dirk\",\"last\":\"Dejesus\",\"id\":37},{\"first\":\"Herbert\",\"last\":\"Homan\",\"id\":38},{\"first\":\"Nicola\",\"last\":\"Narcisse\",\"id\":39},{\"first\":\"Creola\",\"last\":\"Cropp\",\"id\":40},{\"first\":\"Kate\",\"last\":\"Knerr\",\"id\":41},{\"first\":\"Peggie\",\"last\":\"Pyle\",\"id\":42},{\"first\":\"Cicely\",\"last\":\"Cromer\",\"id\":43},{\"first\":\"Cedrick\",\"last\":\"Cacho\",\"id\":44},{\"first\":\"Adelaida\",\"last\":\"Ariza\",\"id\":45},{\"first\":\"Bernardo\",\"last\":\"Brigmond\",\"id\":46},{\"first\":\"Earnestine\",\"last\":\"Ecker\",\"id\":47},{\"first\":\"September\",\"last\":\"Sussman\",\"id\":48},{\"first\":\"Genia\",\"last\":\"Gast\",\"id\":49},{\"first\":\"Hildegarde\",\"last\":\"Hellman\",\"id\":50}]";
        this.getForId_exp = "{\"first\":\"Janice\",\"last\":\"James\",\"id\":36}";
        this.use_id = TestConfiguration.user_id.toString();
    }


}
