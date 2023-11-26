import React, { useEffect, useState } from "react";
import cookie from 'react-cookies';
import Axios from "axios";
import { NavLink } from "react-router-dom";
import moment from "moment";
import LikeIcon from "../Icons/like.png";
import HeartIcon from "../Icons/heart.png";
import CommentIcon from "../Icons/comment.png";

const BACKEND_URL = process.env.REACT_APP_BACKEND_URL
const token = cookie.load('token');
async function check_token() {
    var logged_in = false
    await Axios.get(`${BACKEND_URL}/users`)
        .then(res => {
            (res.data).forEach(i => {
                if (i.token === token) {
                    logged_in = i
                }
            })
        })
        .catch(err => console.log(err));
    return logged_in
}

const Home = () => {
    const [userInfo, setUserInfo] = useState('');
    const [posts, setPosts] = useState([]);
    const [skip, setSkip] = useState(0);
    const [likeInfo, setLikeInfo] = useState({});
    const [totalLikes, setTotalLikes] = useState({});
    const [newestUser, setNewestUser] = useState([]);
    const [taggedList, setTaggedList] = useState([]);
    const [chatList, setChatList] = useState([]);

    useEffect(() => {
        check_token().then(result => {
            if (!result) window.location = "/register";
            else setUserInfo(result);
        })
    }, [])

    useEffect(() => {
        Axios.get(`${BACKEND_URL}/posts/get/allpost`)
        .then(res => {
            setPosts(res?.data)
            // setPosts(posts => [...posts, post])
        })
    }, [userInfo, skip])



    useEffect(() => {
        posts.forEach((post) => {
            Axios.get(`${BACKEND_URL}/likes/get/${post._id}/all`)
                .then(res => {
                    setTotalLikes(n => ({ ...n, [post._id]: res.data.length }));
                    (res.data).forEach((like) => {
                        if (like.liker === userInfo._id) {
                            setLikeInfo(likes => ({
                                ...likes,
                                [post._id]: like._id
                            }))
                        }
                    })
                })
        })
    }, [posts, userInfo])

    const LikePost = (id) => {
        if (!likeInfo[id]) {
            if (userInfo) {
                Axios.post(`${BACKEND_URL}/likes/add`, { liker: userInfo._id, post: id })
                    .then(res => {
                        setLikeInfo(likes => ({
                            ...likes,
                            [id]: res.data.id
                        }))
                        setTotalLikes(n => ({
                            ...n,
                            [id]: n[id] + 1
                        }))
                    })
            }
        }
    }

    const UnlikePost = (id) => {
        if (likeInfo[id]) {
            Axios.delete(`${BACKEND_URL}/likes/remove/${likeInfo[id]}`)
                .then(() => {
                    setLikeInfo(likes => ({
                        ...likes,
                        [id]: null
                    }))
                    setTotalLikes(n => ({
                        ...n,
                        [id]: n[id] - 1
                    }))
                })
        }
    }

    useEffect(() => {
        Axios.get(`${BACKEND_URL}/users/get_newest`)
            .then(res => {
                (res.data).forEach((user) => {
                    setNewestUser(existing => [...existing, user])
                })
            })
            .catch(err => console.log(err));
    }, [])

    useEffect(() => {
        if (userInfo) {
            Axios.get(`${BACKEND_URL}/posts/get/tagged/${userInfo.username}`)
                .then(res => {
                    (res.data).forEach((tag) => {
                        Axios.get(`${BACKEND_URL}/users`)
                            .then(users => {
                                (users.data).forEach((user) => {
                                    if (user._id === tag.user) {
                                        tag.username = user.username;
                                        setTaggedList(ex => [...ex, tag]);
                                    }
                                })
                            })
                    })
                })
        }
    }, [userInfo])

    useEffect(() => {
        if (userInfo) {
            Axios.get(`${BACKEND_URL}/chats/info/${userInfo._id}`)
                .then(res => {
                    res.data.forEach((chat) => {
                        Axios.get(`${BACKEND_URL}/users`)
                            .then(users => {
                                users.data.forEach((user) => {
                                    if (user._id === chat.from) { chat.username = user.username; setChatList(chats => [...chats, chat]); }
                                })
                            })
                    })
                });
        }
    }, [userInfo])

    const GeneratePost = ({ post, index }) => {
        return <div key={index} className="box box-shadow margin-top-bottom">
            <NavLink to={`/post/${post._id}`}><img src={`${BACKEND_URL}/${post.image.filename}`} alt={post.description} className="box-image" /></NavLink>
            <div className="post-section">
                {!likeInfo[post._id]
                    ? <span className="to-like-icon" onClick={() => { LikePost(post._id) }}><img src={LikeIcon} alt="Like Icon" /></span>
                    : <span className="to-like-icon" onClick={() => { UnlikePost(post._id) }}><img src={HeartIcon} alt="Unlike Icon" /></span>}
                <NavLink to={`/post/${post._id}/#comment`}><span className="share-icon"><img src={CommentIcon} alt="Comment Icon" /></span></NavLink>
                <p className="box-text">{totalLikes[post._id]} {totalLikes[post._id] <= 1 ? <span>Like</span> : <span>Likes</span>}</p>
            </div>
            <div className="post-section">
                <p className="box-text">{post.description}</p>
            </div>
        </div>
    }

    return (
        <div className="container" style={{ backgroundColor: "#A9A9A9" }}>
            <div className="home">
                {posts.length !== 0 ? (
                    [posts.map((post, index) => {
                        return <GeneratePost post={post} key={index} />
                    })]
                )
                    : <h1>Loading...</h1>}
            </div>
        </div>
    )
}

export default Home;