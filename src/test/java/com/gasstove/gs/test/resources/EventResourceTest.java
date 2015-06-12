package com.gasstove.gs.test.resources;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.resources.EventResource;
import com.gasstove.gs.test.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;

public class EventResourceTest extends AbstractResourceTest {

    EventResource er = new EventResource(TestConfiguration.db);

    public EventResourceTest(){
        this.clath = Event.class;
        this.resource = new EventResource(TestConfiguration.db);
        this.getAll_exp = "[{\"name\":\"leakier\",\"openDate\":\"1969-12-13T15:56:16.814-08:00\",\"closeDate\":\"1969-12-13T15:56:16.814-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":33,\"id\":1},{\"name\":\"herolike\",\"openDate\":\"1969-12-28T15:45:24.737-08:00\",\"closeDate\":\"1969-12-28T15:45:24.737-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":25,\"id\":2},{\"name\":\"redisbursement\",\"openDate\":\"1969-12-23T10:26:27.448-08:00\",\"closeDate\":\"1969-12-23T10:26:27.448-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":43,\"id\":3},{\"name\":\"obedientiary\",\"openDate\":\"1969-12-15T07:50:47.613-08:00\",\"closeDate\":\"1969-12-15T07:50:47.613-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":35,\"id\":4},{\"name\":\"donalda\",\"openDate\":\"1970-01-09T02:49:19.889-08:00\",\"closeDate\":\"1970-01-09T02:49:19.889-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":15,\"id\":5},{\"name\":\"theistic\",\"openDate\":\"1970-01-16T15:03:58.831-08:00\",\"closeDate\":\"1970-01-16T15:03:58.831-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":47,\"id\":6},{\"name\":\"holt\",\"openDate\":\"1970-01-10T08:45:40.880-08:00\",\"closeDate\":\"1970-01-10T08:45:40.880-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":41,\"id\":7},{\"name\":\"dubiety\",\"openDate\":\"1970-01-14T04:56:53.100-08:00\",\"closeDate\":\"1970-01-14T04:56:53.100-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":13,\"id\":8},{\"name\":\"dahlgren\",\"openDate\":\"1969-12-10T11:02:21.533-08:00\",\"closeDate\":\"1969-12-10T11:02:21.533-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":23,\"id\":9},{\"name\":\"heteropteran\",\"openDate\":\"1970-01-02T13:29:20.559-08:00\",\"closeDate\":\"1970-01-02T13:29:20.559-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":10,\"id\":10}]";
        this.getForId_exp = "{\"name\":\"leakier\",\"openDate\":\"1969-12-13T15:56:16.814-08:00\",\"closeDate\":\"1969-12-13T15:56:16.814-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"ownerId\":33,\"id\":1}";
        this.use_id = TestConfiguration.event_id.toString();
    }

    @Ignore
    @Test
    public void test_getEventsForUser() {
        String response = er.getEventsForUser(TestConfiguration.user_id.toString());
//        String expected = "[{\"name\":\"holt\",\"openDate\":\"1970-01-10T08:45:40.880-08:00\",\"closeDate\":\"1970-01-10T08:45:40.880-08:00\",\"ownerId\":41,\"id\":7}]";
//
//        System.out.println(response);
//        System.out.println(expected);
//
//        assertTrue(response.equals(expected));
    }


}
