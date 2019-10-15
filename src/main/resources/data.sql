insert into Agent (id,name) Values(10,"AgentA"), (11, "AgentB");
INSERT INTO ocean(id, agreeableness, conscientiousness, extraversion, neuroticism, openness, agent_id) VALUES (1, 0.5, 0.6, 0.7, 0.8, 0.15, 1),
(2, 0.3, 0.3, 0.2, 0.4, 0.3, 2);
insert INTO goal_name (id, name) Values (1 , "Succeed in the exam");
insert INTO goal_info (id,  weight, agent_id,goal_name_id) Values (1, 0.5 , 10, 1) ,(2,0.3, 11 ,1);
insert into event_name (id, name, confirmed,event_degree, goal_name_id) Values 
(1, "Negative feedback",false, false, 1),(2, "Positive feedback", false, true, 1) ;