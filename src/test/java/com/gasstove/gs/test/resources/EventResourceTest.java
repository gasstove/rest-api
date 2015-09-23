package com.gasstove.gs.test.resources;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.resources.EventResource;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.Configuration;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class EventResourceTest extends AbstractResourceTest {

    public EventResourceTest(Configuration.FORMAT format){
        this.format = format;
        this.clath = Event.class;
        this.resource = new EventResource(Configuration.getDB());
        this.getAll_exp = "[{\"name\":\"leakier\",\"openDate\":\"1970-01-12T06:12:41.166-08:00\",\"closeDate\":\"1970-01-12T06:12:41.166-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":13,\"id\":1},{\"name\":\"herolike\",\"openDate\":\"1970-01-25T09:57:25.027-08:00\",\"closeDate\":\"1970-01-25T09:57:25.027-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":28,\"id\":2},{\"name\":\"redisbursement\",\"openDate\":\"1970-01-06T08:38:02.729-08:00\",\"closeDate\":\"1970-01-06T08:38:02.729-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":22,\"id\":3},{\"name\":\"obedientiary\",\"openDate\":\"1969-12-11T01:26:39.022-08:00\",\"closeDate\":\"1969-12-11T01:26:39.022-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":29,\"id\":4},{\"name\":\"donalda\",\"openDate\":\"1969-12-23T07:16:08.173-08:00\",\"closeDate\":\"1969-12-23T07:16:08.173-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":28,\"id\":5},{\"name\":\"theistic\",\"openDate\":\"1970-01-04T02:26:02.926-08:00\",\"closeDate\":\"1970-01-04T02:26:02.926-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":36,\"id\":6},{\"name\":\"holt\",\"openDate\":\"1969-12-14T16:12:22.954-08:00\",\"closeDate\":\"1969-12-14T16:12:22.954-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":12,\"id\":7},{\"name\":\"dubiety\",\"openDate\":\"1969-12-08T19:32:58.607-08:00\",\"closeDate\":\"1969-12-08T19:32:58.607-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":1,\"id\":8},{\"name\":\"dahlgren\",\"openDate\":\"1970-01-20T12:43:11.726-08:00\",\"closeDate\":\"1970-01-20T12:43:11.726-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":50,\"id\":9},{\"name\":\"heteropteran\",\"openDate\":\"1969-12-24T13:11:33.198-08:00\",\"closeDate\":\"1969-12-24T13:11:33.198-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":39,\"id\":10}]";
        this.getForId_exp = "{\"name\":\"leakier\",\"openDate\":\"1970-01-12T06:12:41.166-08:00\",\"closeDate\":\"1970-01-12T06:12:41.166-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":13,\"id\":1}";
        this.use_id = TestConfiguration.event_id.toString();
        if( format==Configuration.FORMAT.jsonp ) {
            getAll_exp = callback + "(" + getAll_exp + ");";
            getForId_exp = callback + "(" + getForId_exp + ");";
        }
    }

}
