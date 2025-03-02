package org.mrdarkimc.PAPI;
import java.text.DecimalFormat;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class dFormat extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "dFormat";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MrDarkiMC";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0-SNAPSHOT";
    }

    public dFormat() {
    }
    @Override
    public boolean persist() {
        return true;
    }
    @Override
        public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
            //usage: %dFormat_<decimalFormat>_<dataType>_<originalPlaceholderWithoutBrackets>%
            //usage: %dFormat_#,###_int_statistic_deaths%
            //supported deciminal = any that java.text.DecimalFormat supports
            //supported dataType = int long
        
            //identifier will be "#,###_int_statistic_deaths" for ex
            String[] args = identifier.split("_");
            if (args.length!=3)
                return "Expected3ArgsNot" + args.length;
            String decimal = identifier.substring(0,identifier.indexOf("_"));
            String type = identifier.substring(identifier.indexOf("_")+1,identifier.indexOf("_",identifier.indexOf("_")+1));
            String originPH = "%" + identifier.substring(decimal.length() + type.length() + 2) + "%"; //output %statistic_death%
            DecimalFormat df = new DecimalFormat(decimal);

            String parsedValue = PlaceholderAPI.setPlaceholders(offlinePlayer, originPH);
            try {
                Long lng = Long.parseLong(parsedValue);
                return ((type.equalsIgnoreCase("int") | type.equalsIgnoreCase("long")) && type.equals("int")) ? df.format(Integer.parseInt(parsedValue)) : df.format(lng);
            } catch (NumberFormatException e) {
                return "NumFormatExc";
            }
        }
}