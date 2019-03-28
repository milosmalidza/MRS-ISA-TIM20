package com.webapplication.Model;


public class Friendship {
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//@Column(nullable = false)
	private Long requestReceiverID; //id korisnika koji prima zahtev za prijateljstvo
	
	private Long requestSenderID; //id korisnika koji salje zahtev za prijateljstvo - zbog mapiranja
	
	//@Column(nullable = false)
	private FriendRequest friendRequest;
	
	public Friendship() {}

	public Friendship(Long id, Long requestReceiverID, Long requestSenderID, FriendRequest friendRequest) {
		
		this.id = id;
		this.requestReceiverID = requestReceiverID;
		this.requestSenderID = requestSenderID;
		this.friendRequest = friendRequest;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRequestReceiverID() {
		return requestReceiverID;
	}

	public void setRequestReceiverID(Long requestReceiverID) {
		this.requestReceiverID = requestReceiverID;
	}

	public Long getRequestSenderID() {
		return requestSenderID;
	}

	public void setRequestSenderID(Long requestSenderID) {
		this.requestSenderID = requestSenderID;
	}

	public FriendRequest getFriendRequest() {
		return friendRequest;
	}

	public void setFriendRequest(FriendRequest friendRequest) {
		this.friendRequest = friendRequest;
	}
	
	

}
