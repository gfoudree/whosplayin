CREATE DEFINER=`sql5103427`@`%` PROCEDURE `stp_sel_userFriends`(IN idParam INT)
BEGIN
	SELECT
 Names.username,
 Names.id,
 Names.name,
 Names.age,
 Names.gender,
 Names.location,
 Names.raiting,
 Names.verified,
 Names.dateCreated,
 Names.lastLogin,
 Names.picture,
 Names.gamesPlayed,
 Names.gamesCreated
    
	FROM sql5103427.friendsList as Friends
	JOIN sql5103427.users as Users
	INNER JOIN sql5103427.users as Names
	ON Friends.userId = Users.id
	and Names.id = Friends.friendId
	WHERE Friends.userId = idParam;
END