select agent_id,agent_dest_id,liking_value from agent as ag inner join agent_liking_toward_agent as ag_liking_map inner join liking_towards_agent ag_liking
on ag.id = ag_liking_map.agent_id and ag_liking.id = ag_liking_map.liking_toward_agent_id;
			
            
select 