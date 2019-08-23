insert into Agent (id,name) Values(10,"AgentA"), (11, "AgentB");
INSERT INTO ocean(id, agreeableness, conscientiousness, extraversion, neuroticism, openness, agent_id) VALUES (1, 0.5, 0.6, 0.7, 0.8, 0.15, 1),
(2, 0.3, 0.3, 0.2, 0.4, 0.3, 2);
insert INTO goal_name (id, name) Values (1 , "Succeed in the exam");
insert INTO goal_info (id,  weight, agent_id,goal_name_id) Values (1, 0.5 , 10, 1) ,(2,0.3, 11 ,1);
insert into event_name (id, name, confirmed, goal_name_id) Values 
(1, "Negative feedback", false, 1) ;
insert into event_reaction (id, reaction_name, event_info_id) Values 
(1, "Work Hard", 1) , (2, "Work Harder", 2) ;
insert into event_info (id, event_name_id,event_degree, event_intensity_level, agent_id)
Values (1, 1, false, 0.5,  10) , (2, 1, false, 0.5,  11) ;
insert into action (id, action_degree, approval_degree_level, message, request_or_response, agent_src_id, agent_dest_id, goal_id)
 Values (1, true, 0.3, "asking for help", true, 10, 11, 1),
 (2, false, 0.6, "refusing help", false, 11, 10, 1);