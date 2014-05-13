package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.libs.Json;
import play.mvc.*;

import utils.TimeUtils;
import views.html.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Application extends Controller {
    public static Result getCoordinates() {
        long currentTime = TimeUtils.getCurrentTimeGmt();
        JsonNode json = request().body().asJson();
        JsonNode ids = json.get("ids");
        ObjectNode result = Json.newObject();
        ArrayNode array = result.putArray("users");
        for (int i = 0; i < ids.size(); i++) {
            long id = ids.get(i).asLong();
            User user = User.find.byId(id);
            if (user == null) {
            	continue;
            }
            ObjectNode node = Json.newObject();
            node.put("id", user.id);
            if (TimeUtils.getCurrentTimeGmt() - user.updateTime <= TimeUnit.MINUTES.toMillis(15) &&
                    (user.endTime == -1 || currentTime <= user.endTime)) {
                node.put("alive", true);
                node.put("latitude", user.latitude);
                node.put("longitude", user.longitude);
            } else {
                node.put("alive", false);
            }
            array.add(node);
        }
        return ok(result);
    }

    public static Result getAllUsers() {
        List<User> users = User.all();
        ObjectNode result = Json.newObject();
        ArrayNode array = result.putArray("users");
        for (User user : users) {
            array.add(Json.toJson(user));
        }
        return ok(result);
    }

    public static Result postCoordinates() {
        JsonNode json = request().body().asJson();
        long id = json.get("id").asLong();
        User user = User.find.byId(id);
        if (user == null) {
            user = new User(id);
        }
        user.latitude = json.get("latitude").asDouble();
        user.longitude = json.get("longitude").asDouble();
        user.updateTime = TimeUtils.getCurrentTimeGmt();
        user.save();
        return ok();
    }

    public static Result postDuration() {
        JsonNode json = request().body().asJson();
        long id = json.get("id").asLong();
        User user = User.find.byId(id);
        if (user == null) {
            user = new User(id);
        }
        long duration = json.get("duration").asLong();
        if (duration == -1) {
            user.endTime = -1;
        } else {
            user.endTime = TimeUtils.getCurrentTimeGmt() + duration;
        }
        user.updateTime = TimeUtils.getCurrentTimeGmt();
        user.save();
        return ok();
    }
}
