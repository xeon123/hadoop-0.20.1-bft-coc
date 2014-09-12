/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.json.simple.JSONObject;

/**
 * A JUnit test to test Map-Reduce empty jobs.
 */
public class TestJSONObject extends TestCase {
	/**
	 * Test that a job with no input data (and thus with no input split and no map
	 * task to execute) is successful.
	 * 
	 * @throws IOException
	 */
	public void testEmptyJob() {
		org.apache.hadoop.mapreduce.JobID jobId = new org.apache.hadoop.mapreduce.JobID("job", 1);
		org.apache.hadoop.mapreduce.TaskID tid1 = new org.apache.hadoop.mapreduce.TaskID(jobId, true, 0);
		org.apache.hadoop.mapreduce.TaskID tid2 = new org.apache.hadoop.mapreduce.TaskID(jobId, false, 0);

		Map<String, List<String>> mapHashList = Collections.synchronizedMap(new HashMap<String, List<String>>());
		Map<String, List<String>> redHashList = Collections.synchronizedMap(new HashMap<String, List<String>>());
		List<String> digest = new ArrayList<String>();

		digest.add("d1");
		digest.add("d2");
		digest.add("d3");
		digest.add("d4");
		digest.add("d5");

		mapHashList.put(tid1.toString(), digest);
		redHashList.put(tid2.toString(), digest);

		MajorityVoting voting = new MajorityVoting(0, 0);
		JSONObject obj1 = voting.jsonfy(jobId.toString(), mapHashList, true);
		JSONObject obj2 = voting.jsonfy(jobId.toString(), redHashList, false);
		System.out.println(obj1.toJSONString());
		System.out.println(obj2.toJSONString());

	}

	public void testJSONFailedTasks() {
		JobID jid = new JobID("test", 1);
		TaskID tid = new TaskID(jid, true, 0); 

		VotingSystem voting = new MajorityVoting(1, 1);
		voting.addFirstHash(tid, new String[] {"abc"});
		String actual = voting.jsonfyFailedTasks(jid.toString(), tid.toString(), tid.isMap());
		System.out.println(actual);
		assertEquals("{\"taskid\":\"task_test_0001_m_000000_0\",\"isMap\":\"true\",\"jobid\":\"job_test_0001\"}", actual);
	}
}
