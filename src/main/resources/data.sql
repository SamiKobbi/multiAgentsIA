insert into Agent (id,name) Values(1,"Hiba"), (2,"Sami");
INSERT INTO ocean(id, agreeableness, conscientiousness, extraversion, neuroticism, openness, agent_id)
VALUES (1, 0.4, 0.5, 0.9, 0.1, 0.4, 1), (2, 0.1, 0.2, 0.4, 0.1, 0.3, 2);
insert INTO goal (id, name) Values (1 , "Succeed in the exam");
insert INTO goal_weight (id,  weight, agent_id,goal_id)
Values (1, 0.5 , 1, 1), (2, 0.8 , 2, 1);
insert into event (id, name, confirmed, event_degree, event_intensity_level  , goal_id) Values 
(1, "the exam will be difficult", false,false, 0.4 , 1 ) ,
(2, "the exam will be difficult", false,false, 0.9 , 2 );
insert into event_reaction (id, event_reaction, event_id) Values 
(1, "I am anxious", 1) ,
(2, "I will work harder", 2 );

insert into action (id, action_degree, approval_degree_level, message, request_or_response, agent_src_id, agent_dest_id, goal_id)
 Values (1, true, 0.3, "asking for help", true, 1, 2, 1),
 (2, false, 0.6, "refusing help", false, 2, 1, 1);