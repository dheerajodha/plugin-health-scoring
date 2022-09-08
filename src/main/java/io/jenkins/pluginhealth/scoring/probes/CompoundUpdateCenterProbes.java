package io.jenkins.pluginhealth.scoring.probes;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import io.jenkins.pluginhealth.scoring.model.Plugin;
import io.jenkins.pluginhealth.scoring.model.ProbeResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class CompoundUpdateCenterProbes extends Probe implements UpdateCenterProbe {
    private final List<UpdateCenterProbe> updateCenterProbeList;
    private final ObjectMapper objectMapper;
    private final String updateCenterURL;

    public CompoundUpdateCenterProbes(List<UpdateCenterProbe> updateCenterProbeList,@Value("${jenkins.update-center}") String updateCenterURL) {
        this.updateCenterProbeList = List.copyOf(updateCenterProbeList);
        this.objectMapper = Jackson2ObjectMapperBuilder.json().build();;
        this.updateCenterURL = updateCenterURL;
    }

    @Override
    public void runProbe(Plugin plugin) {
        for (UpdateCenterProbe updateCenterProbe : updateCenterProbeList) {
                updateCenterProbe.runProbe(plugin);
        }
    }

    @Override
    protected ProbeResult doApply(Plugin plugin) {
        runProbe(plugin);
        return null;
    }

    @Override
    public String key() {
        return null;
    }
}
