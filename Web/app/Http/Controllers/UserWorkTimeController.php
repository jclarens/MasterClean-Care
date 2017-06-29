<?php

namespace App\Http\Controllers;

use App\UserWorkTime;
use Illuminate\Http\Request;

class UserWorkTimeController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return UserWorkTime::all();
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $data = $request->all();

        $userWorkTime = UserWorkTime::create($data);

        return response()->json($userWorkTime, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\UserWorkTime  $userWorkTime
     * @return \Illuminate\Http\Response
     */
    public function show(UserWorkTime $userWorkTime)
    {
        return $userWorkTime;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\UserWorkTime  $userWorkTime
     * @return \Illuminate\Http\Response
     */
    public function edit(UserWorkTime $userWorkTime)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\UserWorkTime  $userWorkTime
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, UserWorkTime $userWorkTime)
    {
        $data = $request->all();

        if (array_key_exists('user_id', $data)) {
            $userWorkTime->user_id = $data['user_id'];
        }
        if (array_key_exists('work_time_id', $data)) {
            $userWorkTime->work_time_id = $data['work_time_id'];
        }
        if (array_key_exists('cost', $data)) {
            $userWorkTime->cost = $data['cost'];
        }

        $userWorkTime->save();

        return response()->json($userWorkTime, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\UserWorkTime  $userWorkTime
     * @return \Illuminate\Http\Response
     */
    public function destroy(UserWorkTime $userWorkTime)
    {
        $userWorkTime->delete();

        return response()->json('Deleted', 200);
    }
}
