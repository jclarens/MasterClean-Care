<?php

namespace App\Http\Controllers;

use App\UserJob;
use Illuminate\Http\Request;

class UserJobController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return UserJob::all();
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

        $userJob = UserJob::create($data);

        return response()->json($userJob, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\UserJob  $userJob
     * @return \Illuminate\Http\Response
     */
    public function show(UserJob $userJob)
    {
        return $userJob;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\UserJob  $userJob
     * @return \Illuminate\Http\Response
     */
    public function edit(UserJob $userJob)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\UserJob  $userJob
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, UserJob $userJob)
    {
        $data = $request->all();

        if (array_key_exists('userId', $data)) {
            $userJob->userId = $data['userId'];
        }
        if (array_key_exists('jobId', $data)) {
            $userJob->jobId = $data['jobId'];
        }

        $userJob->save();

        return response()->json($userJob, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\UserJob  $userJob
     * @return \Illuminate\Http\Response
     */
    public function destroy(UserJob $userJob)
    {
        $userJob->delete();

        return response()->json('Deleted', 200);
    }
}
