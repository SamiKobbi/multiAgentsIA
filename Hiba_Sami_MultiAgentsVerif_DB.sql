select agent_id,agent_dest_id,liking_value from agent as ag inner join agent_liking_toward_agent as ag_liking_map inner join liking_towards_agent ag_liking
on ag.id = ag_liking_map.agent_id and ag_liking.id = ag_liking_map.liking_toward_agent_id;
			
            
select * from OCEAN where agent_id=2;

select goal_info.agent_id,goal_name.name, goal_info.weight,ocean.conscientiousness from goal_info inner join goal_name on goal_name.id=goal_info.goal_name_id
inner join ocean on ocean.agent_id=goal_info.agent_id
order by agent_id;

select goal_name.name,event_name.name, event_degree,confirmed,prospected,likelihood from event_name inner join goal_name on goal_name.id=event_name.goal_name_id;

select * from action;
select * from occ where occ.id=114;

select * from occ_sorry_for;
select * from occs_towards_agent;

select event_info.agent_id,goal_name.name,event_name.name,
event_intensity_level,event_reaction.reaction_name,
ocean.neuroticism, ocean.conscientiousness
 from event_info inner join event_name on event_name.id=event_info.event_name_id 
inner join goal_name on goal_name.id = event_name.goal_name_id
inner join event_reaction on event_reaction.event_info_id=event_info.id
inner join ocean on event_info.agent_id=ocean.agent_id
order by agent_id ;


select * from occ;