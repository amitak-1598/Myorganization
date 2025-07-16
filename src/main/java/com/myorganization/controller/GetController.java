package com.myorganization.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import com.myorganization.responsemodel.Response;
import com.myorganization.responsemodel.ResponseFormat;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

@Controller
public class GetController {

	@Inject
	EntityManager em;

	@Inject
	ResponseFormat responseFormat;

	@Transactional
	@Get("/employee")
	public HttpResponse<Response> getEmployee(@QueryValue("source") String source) {
		try {
			String sql = "SELECT * FROM employees";
			Query query = em.createNativeQuery(sql, Tuple.class);

			@SuppressWarnings("unchecked")
			List<Tuple> resultList = query.getResultList();

			List<Map<String, Object>> employees = toList(resultList);
			Response success = responseFormat.getSuccessResponse(employees);
			return HttpResponse.ok(success);

		} catch (Exception ex) {
			ex.printStackTrace();
			Response error = responseFormat.getErrorResponse(ex, "Failed to fetch employee data");
			return HttpResponse.serverError().body(error);
		}
	}

	@Transactional
	@Post("/dynamicorganization")
	public MutableHttpResponse<Map<String, Object>> getDynamicOrgnaization(@Body Map<String, Object> body,
			@QueryValue("source") String source) {
		Map<String, Object> map = new HashMap<>();
		try {
			String include = "";
			boolean requiretotalcount = false;
			String filterquery = "";
//			int limit=0 ;
//			int offset=0 ;
//
//			String sort = "";

			if (body.containsKey("columnInclude")) {
				@SuppressWarnings("unchecked")
				List<String> columninclude = (List<String>) body.get("columnInclude");

				for (int i = 0; i < columninclude.size(); i++) {
					include += columninclude.get(i);
					if (i < columninclude.size() - 1) {
						include += ", ";
					}
				}
			}

			if (body.containsKey("requiretotalcount")) {
				requiretotalcount = true;
			}

			if (body.containsKey("filter")) {
				@SuppressWarnings("unchecked")
				List<Object> filter = (List<Object>) body.get("filter");
				filterquery = function2(filter);
				filterquery = filterquery.replace("startswith", "LIKE");
				filterquery = filterquery.replace("contains", "LIKE");
				filterquery = "(" + filterquery + ")";
			}

//			if (body.containsKey("sort")) {
//				@SuppressWarnings("unchecked")
//				List<Map<String, Object>> sortdata = (List<Map<String, Object>>) body.get("sort");
//				Map<String, Object> sortresponse = sortdata.get(0);
//				String selector = (String) sortresponse.get("selector");
//				sort = sort + selector;
//				Boolean checkselect = (Boolean) sortresponse.get("desc");
//				if (checkselect) {
//					sort = sort + " Desc";
//				} else {
//					sort = sort + " Asc";
//				}
//			}
//
//			if (body.containsKey("skip")) {
//				offset = (int) body.get("skip");
//			}
//			if (body.containsKey("take")) {
//				limit = (int) body.get("take");
//			}

			Query d;
			int total = 0;
			String query = "SELECT " + include + " FROM  employees";

			if (!filterquery.equals("")) {
				query = query + " WHERE " + filterquery;
			}

//			if (!filterquery.equals("")) {
//				query = query + " ORDER BY " + sort;
//			}
//						
//			if (!filterquery.equals("")) {
//				query = query + " OFFSET " + offset;
//			}
//			
//			if (!filterquery.equals("")) {
//				query = query + " LIMIT " + limit;
//			}

			d = em.createNativeQuery(query, Tuple.class);
			@SuppressWarnings("unchecked")
			List<Tuple> list = d.getResultList();
			List<Map<String, Object>> data = toList(list);
			map.put("response", data);

			Query d1;

			if (requiretotalcount) {
				String d1query = "SELECT Count(*) FROM ";
				String queryv = query;
				String[] arrayquery = queryv.split("FROM");
				String d1query2 = d1query + arrayquery[1];
				d1 = em.createNativeQuery(d1query2);
				total = ((Number) d1.getSingleResult()).intValue();
			}
			map.put("count", total);

			return HttpResponse.ok(map);
		} catch (Exception ex) {
			ex.printStackTrace();
			map.put("response", "Something Went Wrong");
			return HttpResponse.badRequest(map);
		}

	}

	public List<Map<String, Object>> convertTupleListToMapList(List<Tuple> tupleList) {
		return tupleList.stream().map(tuple -> {
			Map<String, Object> resultMap = new HashMap<>();
			tuple.getElements().forEach(element -> resultMap.put(element.getAlias(), tuple.get(element.getAlias())));
			return resultMap;
		}).collect(Collectors.toList());
	}

	public List<Map<String, Object>> toList(List<Tuple> resList) {
		List<Map<String, Object>> rows = new ArrayList<>();
		resList.forEach(res -> {
			Map<String, Object> frow = new HashMap<>();
			res.getElements().forEach(col -> {
				Object val = res.get(col);
				if (val instanceof Timestamp) {
					Timestamp timestamp = (Timestamp) val;
					LocalDateTime dateTime = timestamp.toLocalDateTime();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					val = dateTime.format(formatter);
				}
				frow.put(col.getAlias(), val);
			});
			rows.add(frow);
		});
		return rows;
	}

	static String function2(List<Object> filterdata) {

		String qq = "";

		for (int i = 0; i < filterdata.size(); i++) {
			String ArrayList = filterdata.get(i).getClass().getSimpleName().toString();

			if (ArrayList.equals("ArrayList")) {
				@SuppressWarnings("unchecked")
				List<Object> innerlist = (List<Object>) filterdata.get(i);
				int size = filterdata.size();

				if (size == 3) {
					String innervalue = innerlist.get(2).getClass().getSimpleName().toString();
					String vv = "";
					if (innervalue.equals("String")) {
						if (innerlist.get(1).equals("startswith")) {
							vv = (String) innerlist.get(2);
							if (!vv.contains("%") && !vv.contains("'")) {
								vv = "'" + vv + "%'";
								innerlist.set(2, vv);
							}
						}

						if (innerlist.get(1).equals("contains")) {
							vv = (String) innerlist.get(2);
							if (!vv.contains("%") && !vv.contains("'")) {
								vv = "'%" + vv + "%'";
								innerlist.set(2, vv);
							}
						}
					}
				}
				qq += function2(innerlist) + " ";
			} else if (ArrayList.equals("String")) {
				if (filterdata.get(i).equals("and")) {
					qq += ")" + filterdata.get(i) + "(" + " ";
				} else {
					qq += filterdata.get(i) + " ";
				}
			} else {
				qq += filterdata.get(i) + " ";
			}

		}

		return qq;

	}

	static String function3(List<Object> filterData) {
		StringBuilder result = new StringBuilder();

		for (Object item : filterData) {
			if (item instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> innerList = (List<Object>) item;

				if (innerList.size() == 3 && innerList.get(2) instanceof String) {
					String innerValue = (String) innerList.get(2);

					if (!innerValue.contains("%") && !innerValue.contains("'")) {
						if ("startswith".equals(innerList.get(1))) {
							innerValue = "'" + innerValue + "%'";
						} else if ("contains".equals(innerList.get(1))) {
							innerValue = "'%" + innerValue + "%'";
						}
						innerList.set(2, innerValue);
					}
					result.append(function2(innerList)).append(" ");
				}
			} else if (item instanceof String) {
				if ("and".equals(item)) {
					result.append(") ").append(item).append(" ( ");
				} else {
					result.append(item).append(" ");
				}
			} else {
				result.append(item).append(" ");
			}
		}

		return result.toString();
	}

}
