package mka.coffeshopmanagementsystem.model.persistence;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import mka.coffeshopmanagementsystem.model.people.*;
import mka.coffeshopmanagementsystem.model.payment.*;

/**
 * Provides a unified Gson instance configured with all necessary adapters.
 */
public class GsonProvider {

    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Employee.class, new EmployeeAdapter())
                .registerTypeAdapter(Payment.class, new PaymentAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
    }

    private static class EmployeeAdapter implements JsonDeserializer<Employee>, JsonSerializer<Employee> {
        @Override
        public Employee deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement roleElement = jsonObject.get("role");

            if (roleElement == null) {
                throw new JsonParseException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.json.err_role"));
            }

            String role = roleElement.getAsString();
            switch (role) {
                case "Cashier":
                    return context.deserialize(json, Cashier.class);
                case "Chef":
                    return context.deserialize(json, Chef.class);
                case "Barista":
                    return context.deserialize(json, Barista.class);
                case "Waiter":
                    return context.deserialize(json, Waiter.class);
                default:
                    throw new JsonParseException(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.json.err_unknown_role"), role));
            }
        }

        @Override
        public JsonElement serialize(Employee src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src, src.getClass());
        }
    }

    private static class PaymentAdapter implements JsonDeserializer<Payment>, JsonSerializer<Payment> {
        @Override
        public Payment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement typeElement = jsonObject.get("type");

            if (typeElement == null) {
                throw new JsonParseException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.json.err_type"));
            }

            String paymentType = typeElement.getAsString();
            switch (paymentType) {
                case "CASH":
                    return context.deserialize(json, Cash.class);
                case "CREDIT_CARD":
                    return context.deserialize(json, CreditCard.class);
                case "TRANSFER":
                    return context.deserialize(json, Transfer.class);
                default:
                    throw new JsonParseException(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.json.err_unknown_type"), paymentType));
            }
        }

        @Override
        public JsonElement serialize(Payment src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src, src.getClass());
        }
    }

    private static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString());
        }
    }
}
