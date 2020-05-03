package omv.server.controllers;

import java.util.ArrayList;
import java.util.Set;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import omv.server.App;
import omv.server.actions.VisualizationAction;

public class VisualizationController {
	RtxManager rtxmanager;

	public VisualizationController(RoutingContext rtx) {
        rtxmanager = new RtxManager(rtx);
    }

	public void post() {
        MultiMap attributes = this.rtxmanager.rtx.request().formAttributes();
        Set<FileUpload> uploads = this.rtxmanager.rtx.fileUploads();
        FileUpload owl1=null;
        FileUpload owl2=null;
        FileUpload mappings=null;
        if ((uploads != null && uploads.size() == 3)) {
            for(FileUpload upload : uploads) {
                owl1 = upload.name().equals("owl1") ? upload : owl1;
                owl2 = upload.name().equals("owl2") ? upload : owl2;
                mappings = upload.name().equals("mappings") ? upload : mappings;
            };
            if (owl1 != null && owl1.contentType().equals("application/rdf+xml") &&
                owl2 != null && owl2.contentType().equals("application/rdf+xml") &&
                mappings != null && mappings.contentType().equals("application/json")) {
                new VisualizationAction().create(owl1.uploadedFileName(),
                                                 owl2.uploadedFileName(),
                                                 mappings.uploadedFileName()).onComplete((ar) -> {
                    if (ar.succeeded()) {
                        this.rtxmanager.responsebody.put("success", true);
                        this.rtxmanager.sendResponse();
                    } else {
                        this.rtxmanager.fail(ar.cause());
                    }
                });
            } else {
                this.rtxmanager.fail(new Throwable("400::"));
            }
        } else {
            this.rtxmanager.fail(new Throwable("400::"));
        }
    }
}