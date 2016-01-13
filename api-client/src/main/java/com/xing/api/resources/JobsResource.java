/*
 * Copyright (С) 2015 XING AG (http://xing.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xing.api.resources;

import com.xing.api.CallSpec;
import com.xing.api.HttpError;
import com.xing.api.Resource;
import com.xing.api.XingApi;
import com.xing.api.data.jobs.Job;
import com.xing.api.data.jobs.PartialJob;

import java.util.List;

/**
 * @author daniel.hartwich
 */
public class JobsResource extends Resource {
    /**
     * Creates a resource instance. This should be the only constructor declared by child classes.
     */
    protected JobsResource(XingApi api) {
        super(api);
    }

    /**
     * Returns a full {@linkplain Job} posting.
     *
     * Full job postings contain the following fields in addition to minimal job postings (returned by jobs
     * recommendations and jobs search ): level, job_type, industry, skills, tags, description.
     * When the contact field is present, it contains either a company or a user.
     * Warning: The company field does not contain a XING company profile in all cases. This means that the company,
     * that posted the job, does not have a XING company profile. In this case the id field is null and the links field
     * contains an empty object.
     */
    public CallSpec<Job, HttpError> getJobById(String jobId) {
        return Resource.<Job, HttpError>newGetSpec(api, "/v1/jobs/{id}")
              .pathParam("id", jobId)
              .responseAs(single(Job.class, "job"))
              .build();
    }

    public CallSpec<List<PartialJob>, HttpError> getJobsByCriteria(String criteria) {
        return Resource.<List<PartialJob>, HttpError>newGetSpec(api, "/v1/jobs/find")
              .queryParam("query", criteria)
              .responseAs(list(PartialJob.class, "jobs", "items"))
              .build();
    }

    public CallSpec<List<PartialJob>, HttpError>  getJobsRecomendationsForUser(String userId) {
        return Resource.<List<PartialJob>, HttpError>newGetSpec(api, "/v1/users/{user_id}/jobs/recommendations")
              .pathParam("user_id", userId)
              .responseAs(list(PartialJob.class, "job_recommendations", "items"))
              .build();
    }
}
